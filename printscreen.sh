chmod +x ./printscreen.sh
now=$(date +"%s")
path="/sdcard/"
pngname="printscreen"
subfilename=".png"
filename=$path$pngname$now$subfilename
echo "$filename"
adb shell screencap -p "$filename"
adb pull "$filename"
adb shell rm "$filename"
