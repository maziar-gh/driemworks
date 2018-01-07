package com.driemworks.ar.dto;

import android.util.Log;

import com.driemworks.ar.utils.CvUtils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * The camera pose dto
 *
 * The camera pose consists of a coordinate in 3-space
 * and a direction in said space relative to a preset position
 *
 * @author Tony
 */
public class CameraPoseDTO {

    /**
     * The coordinate
     */
    private Mat coordinate;

    /**
     * The direction
     */
    private Mat direction;

    /**
     * The default constructor
     */
    public CameraPoseDTO() {
        super();
        reset();
    }

    /**
     * Constructor for the CameraPoseDTO
     * @param coordinate The coordinate
     * @param direction The direction
     */
    public CameraPoseDTO(Mat coordinate, Mat direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }

    /**
     * Update the coordinate and direction using a translation vector and a rotation matrix
     * @param translation The 3 x 1 translation vector
     * @param rotation The 3 x 3 rotation matrix
     */
    public void update(Mat translation, Mat rotation) {
        Log.d("rotation: ", "" + rotation);
        Log.d("translation: ", "" + translation);
        this.direction = CvUtils.mult(this.direction, rotation);
        this.coordinate = CvUtils.add(this.coordinate, CvUtils.mult(this.direction, translation));
    }

    /**
     * Set the coordinate to (0,0,0)
     * Set the direction to the identity matrix
     */
    public void reset() {
        resetDirection();
        resetCoordinate();
    }

    /**
     * Set the coordinate to (0,0,0)
     */
    private void resetCoordinate() {
        this.coordinate = new Mat(3, 1, CvType.CV_64FC1);
        coordinate.setTo(new Scalar(0, 0,0));
    }

    /**
     * Set the direction to the (3x3) identity matrix
     */
    private void resetDirection() {
        this.direction = new Mat(3,3, CvType.CV_64FC1);
        direction.setTo(new Scalar(0, 0, 0));

        double[] one = {1};
        direction.put(0, 0, one);
        direction.put(1, 1, one);
        direction.put(2, 2, one);
    }

    @Override
    public String toString() {
        return "CameraPoseDTO: " + "current coordinate: "
                + CvUtils.printMat(coordinate) + "current direction: " + CvUtils.printMat(direction);
    }

    /**
     * Getter for the coordinate
     * @return coordinate The coordinate
     */
    public Mat getCoordinate() {
        return coordinate;
    }

    /**
     * Setter for the coordinate
     * @param coordinate The coordinate to set
     */
    public void setCoordinate(Mat coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Getter for the direction
     * @return direction
     */
    public Mat getDirection() {
        return direction;
    }

    /**
     * Setter for the direction
     * @param direction The direction to set
     */
    public void setDirection(Mat direction) {
        this.direction = direction;
    }
}
