package org.firstinspires.ftc.teamcode.dchelpers;

//import edu.wpi.first.networktables.NetworkTableInstance;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Clase simple para leer AprilTags con la Limelight.
 * No es un modo operativo y solo obtiene todos los ID detectados.
 */
//public class limelighthelper {

  //  private final String limelightName; // normalmente "limelight"

    //public limelighthelper() {
  //      this("limelight"); // nombre por defecto
    //}

    //public limelighthelper(String limelightName) {
      //  this.limelightName = limelightName;
    //}

    /**
     * Devuelve true si la Limelight está viendo al menos un AprilTag.
     */
  //  public boolean hasTarget() {
      //  return LimelightHelpers.getTV(limelightName);
   // }

    /**
     * Devuelve el ID del AprilTag principal que está detectando.
     * Si no hay target, devuelve -1.
     */
    //public int getPrimaryTagId() {
      //  if (!hasTarget()) {
        //    return -1;
        //}
        //return (int) LimelightHelpers.getFiducialID(limelightName);
   // }

    /**
     * Devuelve un arreglo con TODOS los ID de AprilTags que está viendo
     * en este momento (puede haber varios).
     */
    //public int[] getAllTagIds() {
      //  if (!hasTarget()) {
       //     return new int[0];
        // }

        // Obtiene el JSON completo de resultados
        // LimelightHelpers.LimelightResults results = LimelightHelpers.getLatestResults(limelightName);

        // if (results == null || results.targets_Fiducials == null) {
           // return new int[0];
        // }

        //int[] ids = new int[results.targets_Fiducials.length];
        //for (int i = 0; i < results.targets_Fiducials.length; i++) {
          //  ids[i] = (int) results.targets_Fiducials[i].fiducialID;
        //}
        //return ids;
    //}

    /**
 //    * Mé // todo de utilidad para imprimir o mostrar en SmartDashboard
     */
   // public void updateDashboard() {
     //   SmartDashboard.putBoolean("Limelight Has Target", hasTarget());
       // SmartDashboard.putNumber("Primary Tag ID", getPrimaryTagId());

       // int[] allIds = getAllTagIds();
       // SmartDashboard.putString("All Tag IDs", java.util.Arrays.toString(allIds));
   // }
// }
