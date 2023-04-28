JFLAGS = -g
SRCDIR = src
BINDIR = bin
JC = javac

.SUFFIXES: .java .class


$(BINDIR)/%.class : $(SRCDIR)/%.java
	$(JC) -d $(BINDIR)/ $(SRCDIR)/*.java

CLASSES = \
	$(BINDIR)/GraphException.class \
	$(BINDIR)/Path.class \
	$(BINDIR)/Edge.class \
	$(BINDIR)/Vertex.class \
	$(BINDIR)/GUI.class \
	$(BINDIR)/Graph.class

default: $(CLASSES)

classes: $(CLASSES:.java=.class)

clean:
	$(RM) $(BINDIR)/*.class
	
run:
	java -cp $(BINDIR) Graph