echo Original file contents:
cat $1
echo $'\n'
echo After BWT encode and decode:
./burrows_wheeler.sh - < $1 | ./burrows_wheeler.sh +
echo $'\n'
echo After MTF encode and decode:
./move_to_front.sh - < $1 | ./move_to_front.sh +
echo $'\n'
