package ing.hackaton.model;

/**
 * Created by hosinglee on 19/05/16.
 */
public class DivisionDTO {

    private String description;

    private Integer code;

    public DivisionDTO(Integer code, String description) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCode() {
        return code;
    }

}
