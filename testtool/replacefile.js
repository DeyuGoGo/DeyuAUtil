const testFolder = './';
const fs = require('fs');
const lineReader = require('readline');
// var mkdirp = require('mkdirp');
// mkdirp('build', function(err) {
//
// });
console.log("go");
fs.readdir(testFolder, (err, files) => {
  files.forEach(file => {
    var res = "" , bs = "";
    var final = "";
    if(!file.includes('.xml'))
    return;
    console.log(file);
    ok = lineReader.createInterface({
      input: fs.createReadStream(file)
    });
    ok.on('line' , function(line){
      tmp = line.replace("com.example.unood.custom_view.MyTextView","TextView")+'\n';
        res+=tmp;
        return;
    });
    ok.on('close',()=>{
      var stream = fs.createWriteStream('./build/'+file);
      stream.once('open', function(fd) {
      stream.write(res);
      stream.end();
    });
  });
});
});
