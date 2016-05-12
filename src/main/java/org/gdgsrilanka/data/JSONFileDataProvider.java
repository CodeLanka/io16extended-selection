package org.gdgsrilanka.data;

import org.gdgsrilanka.models.Participant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by Tharu on 2016-05-11.
 */
public class JSONFileDataProvider implements DataProvider {

    private BufferedReader reader;
    private JSONArray responsesArray;
    private int currentArrayPosition = 0;

    public JSONFileDataProvider(String fileName) {
        try {
            File f = new File(fileName);
            System.out.println(f.getAbsolutePath());
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            loadJSON();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadJSON() {
        try {
            String readLine = reader.readLine();
            if (readLine != null) {
                this.responsesArray = new JSONArray(readLine);
                System.out.println(this.responsesArray.length() + " entries found");
            } else {
                System.out.println("JSON INPUT ERROR");
            }

            System.out.println("entries loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the next available participants
     * @return
     */
    public Participant getParticipant() {
        if(currentArrayPosition < responsesArray.length()) {
            JSONObject entry = parseParticipantEntry(responsesArray.getJSONObject(currentArrayPosition));

            Participant participant = new Participant(
                    entry.getString("name"),
                    entry.getString("email"),
                    entry.getInt("iocount"),
                    entry.getBoolean("ideamart"),
                    entry.getBoolean("playstore"),
                    entry.getInt("score"));

            //set the non-constructed vars which were later added
            participant.setEventRating(entry.getInt("event_rating"));
            participant.setId(entry.getString("id"));
            participant.setInstitute(entry.getString("institute"));
            participant.setNic(entry.getString("nic"));
            participant.setContact(entry.getString("contact"));
            participant.setGender(entry.getString("gender"));

            currentArrayPosition++;
            return participant;
        } else {
            return null;
        }
    }


    /**
     * Parses the JSON Object which contain one entry and parses it for the required info
     * @param object
     * @return
     */
    private JSONObject parseParticipantEntry(JSONObject object) {
        JSONObject out = new JSONObject();
        out.put("id", object.get("id"));
        out.put("name", object.get("name"));
        out.put("email", object.get("email"));
        out.put("score", object.getInt("score"));
        out.put("event_rating", object.getInt("event_rating"));
        out.put("nic", object.get("nic"));
        out.put("gender", object.get("gender"));


        String responseString = object.getString("response");

        JSONArray responseStringArray = new JSONArray(responseString.replaceAll("\\\\", ""));

        JSONObject contactObj = selectJSONWithName("3", responseStringArray);
        if(contactObj != null) {
            out.put("contact",contactObj.getString("value"));
        } else {
            out.put("contact", "");
        }

        JSONObject instituteObj = selectJSONWithName("4", responseStringArray);
        if(instituteObj != null) {
            out.put("institute",instituteObj.getString("value"));
        } else {
            out.put("institute", "");
        }

        JSONObject ideamartObj = selectJSONWithName("11", responseStringArray);
        if(ideamartObj != null) {
            out.put("ideamart",
                    (ideamartObj.getString("value").equalsIgnoreCase("YES")));
        } else {
            out.put("ideamart", false);
        }

        JSONObject playObj = selectJSONWithName("13", responseStringArray);
        if (playObj != null) {
            out.put("playstore", playObj.getString("value").equalsIgnoreCase("YES"));
        } else {
            out.put("playstore", false);
        }

        try {
            JSONObject ioObject = selectJSONWithName("10", responseStringArray);
            if(ioObject != null) {
                if (ioObject.get("value") instanceof JSONArray) {
                    JSONArray ioArray = selectJSONWithName("10", responseStringArray).getJSONArray("value");
                    out.put("iocount", ioArray.length());

                } else {
                    String ioArray = selectJSONWithName("10", responseStringArray).getString("value");
                    if (ioArray.length() > 0) {
                        out.put("iocount", 1);
                    } else {
                        out.put("iocount", 0);
                    }
                }
            } else {
                out.put("iocount", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }


    private JSONObject selectJSONWithName(String name, JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("name").equalsIgnoreCase(name)) {
                //System.out.println(array.getJSONObject(i).toString());
                return array.getJSONObject(i);
            }
        }

        return null;
    }

}
