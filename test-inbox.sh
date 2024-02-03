#!/bin/bash

if [[ $# -lt 1 ]]; then
    echo "Usage: ${0} <target_username>"
    exit 1
fi

# parameters
TARGET="${1}"
SENDER="test_sender$$"
MESSAGE="this is a script test! date: $(date)"

# helper functions to write java-compatible strings
function writeNumber() {
    NUMBER="${1}"
    OUTPUT="${2}"
    printf "%04x" "$NUMBER" | xxd -r -p >> "$OUTPUT"
}
function writeString() {
    VALUE="${1}"
    OUTPUT="${2}"
    LEN="${#VALUE}"
    writeNumber $LEN $OUTPUT
    echo -n "$VALUE" >> "$OUTPUT"
}

# Set the filename
filename="/tmp/message_data.bin"
rm -f $filename
touch $filename

# write our message
writeString "$SENDER" $filename
writeString "$TARGET" $filename
writeString "$MESSAGE" $filename

# send data to app running on our machine
PORT=5678
HOST="localhost"
nc "$HOST" "$PORT" < "$filename"

rm -f $filename
