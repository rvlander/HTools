wacdump_LIBS  = -lncurses 
jwintab_LIBS = -lpthread 
CFLAGS = -D_REENTRANT -fPIC
jwintab_INCLUDES = -I/usr/lib/jvm/java-7-openjdk/include -I/usr/lib/jvm/java-7-openjdk/include/linux 

# Should be equivalent to your list of C files, if you don't build selectively
wacdump_SRC= wacusb.c wactablet.c wacserial.c wacdump.c wacscrn.c
jwintab_SRC= Jwintab.c mywintab.c wacusb.c wactablet.c wacserial.c

all: libjwintab.so jwintab_test
	mv libjwintab.so ../

wacdump: $(wacdump_SRC)
	gcc -o $@ $^ $(CFLAGS) $(wacdump_LIBS)

libjwintab.so: $(jwintab_SRC)
	gcc -o $@ -shared $^ $(CFLAGS) $(jwintab_LIBS) $(jwintab_INCLUDES) -Wl,-soname,$@	
jwintab_test: $(jwintab_SRC)
	gcc -o $@ $^ $(CFLAGS) $(jwintab_LIBS) $(jwintab_INCLUDES)

clean:
	rm wacdump jwintab_test ../libjwintab.so



