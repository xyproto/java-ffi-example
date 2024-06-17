package main;

import static java.lang.foreign.ValueLayout.*;
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

class LibC {
    private final Arena allocator;
    Linker linker = Linker.nativeLinker();
    SymbolLookup lib = linker.defaultLookup();

    MemorySegment fopenAddress = lib.find("fopen").orElseThrow();
    // FILE *fopen(const char *filename, const char *mode)
    FunctionDescriptor fopenDesc = FunctionDescriptor.of(ADDRESS, ADDRESS, ADDRESS);
    MethodHandle fopen = linker.downcallHandle(fopenAddress, fopenDesc);

    //char *fgets(char *str, int n, FILE *stream)
    MemorySegment fgetsAddress = lib.find("fgets").orElseThrow();
    FunctionDescriptor fgetsDesc =
    		FunctionDescriptor.of(ADDRESS, ADDRESS, JAVA_INT, ADDRESS);
    MethodHandle fgets = linker.downcallHandle(fgetsAddress, fgetsDesc);

    //int fclose(FILE *stream)
    MemorySegment fcloseAddress = lib.find("fclose").orElseThrow();
    FunctionDescriptor fcloseDesc =
    		FunctionDescriptor.of(JAVA_INT, ADDRESS);
    MethodHandle fclose = linker.downcallHandle(fcloseAddress, fcloseDesc);

    LibC(Arena arena) {
        this.allocator = arena;
    }

    MemorySegment fopen(String filePath, String mode) {
    	MemorySegment pathPtr = allocator.allocateFrom(filePath);
    	MemorySegment modePtr = allocator.allocateFrom(mode);

    	try {
    		return (MemorySegment) fopen.invoke(pathPtr, modePtr);
    	}
    	catch (Throwable e) {
    		return MemorySegment.NULL;
    	}
    }

    MemorySegment fgets(MemorySegment buffer, int size, MemorySegment filePtr) {
    	try {
    		return (MemorySegment) fgets.invoke(buffer, size, filePtr);
    	}
    	catch (Throwable e) {
    		return MemorySegment.NULL;
    	}
    }

    int fclose(MemorySegment filePtr) {
    	try {
    		return (int) fclose.invoke(filePtr);
    	}
    	catch (Throwable e) {
    		return -1;
    	}
    }
}
