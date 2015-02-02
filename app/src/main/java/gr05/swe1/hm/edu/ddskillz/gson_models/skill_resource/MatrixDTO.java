package gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Created by Peter on 26.11.2014.
 */
public class MatrixDTO {

    @Expose
    private String message;
    @Expose
    private int httpStatus;
    @Expose
    private List<Matrix> matrix = new ArrayList<Matrix>();

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus The httpStatus
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @return The matrix
     */
    public List<Matrix> getMatrix() {
        return matrix;
    }

    /**
     * @param matrix The matrix
     */
    public void setMatrix(List<Matrix> matrix) {
        this.matrix = matrix;
    }

}