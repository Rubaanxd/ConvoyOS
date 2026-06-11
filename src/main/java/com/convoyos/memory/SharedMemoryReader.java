package com.convoyos.memory;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class SharedMemoryReader {

    private static final String MMF_NAME = "Local\\SCSTelemetry";

    public boolean connect() {

        HANDLE handle = Kernel32.INSTANCE.OpenFileMapping(
                Kernel32.FILE_MAP_READ,
                false,
                MMF_NAME);

        if (handle == null) {
            System.out.println("OpenFileMapping devolvió null");
            return false;
        }

        long pointerValue = Pointer.nativeValue(handle.getPointer());

        if (pointerValue == 0) {
            System.out.println("Handle inválido");
            return false;
        }

        System.out.println("Handle obtenido correctamente");

        Kernel32.INSTANCE.CloseHandle(handle);

        return true;
    }
}