package org.firstinspires.ftc.teamcode.vision;

public class HiveVisionConfig {
    public static final String TAG_FAMILY = "36h11";
    // El tamaño del tag puede ser variable, lo hacemos configurable
    public static double TAG_SIZE_METERS = 0.08255; 
    public static final int TAGS_PER_CELL = 4;

    public enum HiveCell {
        RED_SCORING(30, 31, 32, 33),
        RED_AUDIENCE(34, 35, 36, 37),
        BLUE_AUDIENCE(38, 39, 40, 41),
        BLUE_SCORING(42, 43, 44, 45),
        UNKNOWN();

        public final int[] tagIds;

        HiveCell(int... ids) {
            this.tagIds = ids;
        }

        public static HiveCell getFromId(int id) {
            for (HiveCell cell : values()) {
                for (int tagId : cell.tagIds) {
                    if (tagId == id) return cell;
                }
            }
            return UNKNOWN;
        }
    }
}
