
var rotateRight = function(head, k) {
    temp = [];
    result = [];
    while(head === null){
        temp.push(head.val);
        head = head.next;
    }
    for (var i = 0; i < temp.length; i++) {
      result.push(temp[((i+k) % temp.length)]);
    }
    return result;
};
console.log(rotateRight([1,2,3,4,5,6,7],4));
