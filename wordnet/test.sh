echo Running tests for WordNet.
java -ea -classpath classes:../lib/algs4.jar:../lib/stdlib.jar WordNet

echo Running tests for SAP.
java -ea -classpath classes:../lib/algs4.jar:../lib/stdlib.jar SAP \
    data/digraph1.txt
