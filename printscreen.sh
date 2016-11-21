# 設定檔案權限
chmod +x ./printscreen.sh
# 變數now為timestamp
now=$(date +"%s")
# 設定要存在手機哪個資料夾下。(不過存到ＰＣ後就會下只另刪除是沒差，只要權限ok暫存的資料夾就好)
path="/sdcard/"
# 娶個看得懂的名字
pngname="printscreen"
# 副檔名
subfilename=".png"
# 連起來
filename=$path$pngname$now$subfilename
# echo儲存的路徑。
echo "$filename"
# Android4.0以上才有提供的command screencap到指定路徑。
adb shell screencap -p "$filename"
# 存手機複製出指定路徑的檔案
adb pull "$filename"
# 刪除手機內的檔案。
adb shell rm "$filename"
