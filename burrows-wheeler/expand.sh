sh huffman.sh + < $1 | sh move_to_front.sh + | sh burrows_wheeler.sh + > $2
