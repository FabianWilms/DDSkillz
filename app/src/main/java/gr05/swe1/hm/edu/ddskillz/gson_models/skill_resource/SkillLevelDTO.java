
package gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public class SkillLevelDTO {
    @Expose
    private String skillName;
    @Expose
    private int skillValue;
    @Expose
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The skillName
     */
    public String getSkillName() {
        return skillName;
    }

    /**
     * @param skillName The skillName
     */
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    /**
     * @return The skillValue
     */
    public int getSkillValue() {
        return skillValue;
    }

    /**
     * @param skillValue The skillValue
     */
    public void setSkillValue(int skillValue) {
        this.skillValue = skillValue;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString(){
        return skillName+": "+skillValue;
    }

}
