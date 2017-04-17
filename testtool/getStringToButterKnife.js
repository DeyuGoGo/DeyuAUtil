const testFolder = './';
const fs = require('fs');
const lineReader = require('readline');
var re = /getString\(R\./ ;
var re_arg = /getArguments()/ ;
var rex2 = /(\(R\.string\.[\w]+\))/;
var rex3 = /\(R\.string\.([\w]+)\)/;
var rex4 = /(getString\(R\.string\.[\w]+\))/;
var rex5 = /(public[\s]*class[\w\s]*{)/;
fs.readdir(testFolder, (err, files) => {
  files.forEach(file => {
    var res = "" , bs = "";
    var final = "";
    var butterArray = [];
    ok = lineReader.createInterface({
      input: fs.createReadStream(file)
    });
    ok.on('line' , function(line){
      tmp = line.replace("getActivity()","mActivity")+'\n';
      if(tmp.match(re_arg) !==null){
        res+=tmp;
        return;
      }
      templine = tmp;
      while(tmp.match(re)!== null){
        butterArray.push(getButterString(tmp));
        tmp = tmp.replace(tmp.match(rex4)[1],getStringName(tmp));
      }
      res+=tmp;
    });
    ok.on('close',()=>{
      console.log(butterArray);
      butterArray.forEach(function (data,index,err){
        bs+='\n'+data;
      });
      if(res.match(rex5)===null)
      return;
      var stream = fs.createWriteStream('test'+file);
      stream.once('open', function(fd) {
      stream.write(res.replace(res.match(rex5)[1],res.match(rex5)[1]+bs));
      stream.end();
    });
    });
  });
})

function getStringName(line){
  return 'mStr'+line.match(rex3)[1];
}

function getButterString(line){
  res = line.match(rex2);
  return "@BindString" + res[1]+'String ' + getStringName(line)+';';
}
