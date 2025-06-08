package dev.mkomarov.task3.model;

import java.util.UUID;

public class ThumbnailTask {
    private final String id;
    private final String inputPath;
    private final String outputPath;
    private final int targetWidth;
    private final int targetHeight;

    public ThumbnailTask(String inputPath, String outputPath, int targetWidth, int targetHeight) {
        this.id = UUID.randomUUID().toString();
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    public String getId() {
        return id;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    @Override
    public String toString() {
        return String.format("ImageTask[id=%s, in=%s, out=%s, %dx%d]",
                id, inputPath, outputPath, targetWidth, targetHeight);
    }
}
