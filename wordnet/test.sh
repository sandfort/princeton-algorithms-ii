echo Running tests for WordNet.
java -ea -classpath classes:../lib/algs4.jar:../lib/stdlib.jar WordNet \
    data/synsets.txt \
    data/hypernyms.txt

# echo Running tests for SAP \(^D to skip\)
# java -ea -classpath classes:../lib/algs4.jar:../lib/stdlib.jar SAP $1
