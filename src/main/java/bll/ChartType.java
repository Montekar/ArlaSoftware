package bll;

public enum ChartType {
    PIE,
    BAR,
    LINE;

    public static ChartType getTypeFromString(String chartType) {
        switch (chartType) {
            case "PIE":
                return ChartType.PIE;
            case "BAR":
                return ChartType.BAR;
            case "LINE":
                return ChartType.LINE;
            default:
                throw new RuntimeException("Wrong chart type in DB");
        }
    }
}
