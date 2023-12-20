package org.ssustudents.models;

import lombok.Data;
import org.ssustudents.App;
import oshi.hardware.HWDiskStore;
import oshi.util.FormatUtil;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.List;

@Data
public class Disk {
    private String model;
    private long totalSpace;
    private long usedSpace;
    private long freeSpace;

    public Disk() {
//        updateInfo();
    }
    public void updateInfo(){
        List<HWDiskStore> diskStores = App.si.getHardware().getDiskStores();
        HWDiskStore diskStore = diskStores.get(0);
        model = diskStore.getModel();
        totalSpace = diskStore.getSize();

        try {
            usedSpace = 0;
            for (FileStore store : FileSystems.getDefault().getFileStores()) {
                long storeTotalSpace = store.getTotalSpace();
                long storeFreeSpace = store.getUsableSpace();
                long storeUsedSpace = storeTotalSpace - storeFreeSpace;
                usedSpace += storeUsedSpace;
            }
            freeSpace = totalSpace - usedSpace;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
