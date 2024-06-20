package main;

import java.lang.foreign.*;

class Runner {

    public static void main(String[] args) {
        try (Arena allocator = Arena.ofConfined()) {
            var libc = new LibC(allocator);

            MemorySegment filePtr = libc.fopen("result.txt", "r");
            if (filePtr.equals(MemorySegment.NULL)) {
                System.out.println("File could not be opened or invoke call failed");
                return;
            }
            MemorySegment buffer = allocator.allocate(100);
            MemorySegment result;
            for (; ; ) {
                result = libc.fgets(buffer, 100, filePtr);
                if (result.equals(MemorySegment.NULL)) break;

                System.out.print(buffer.getString(0));
            }

            if (libc.fclose(filePtr) != 0) {
                System.out.println("File was not closed");
            }
        }
    }
}
