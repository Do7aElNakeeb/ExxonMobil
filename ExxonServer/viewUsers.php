<html>
<head><title>View Users</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<style>
body {
  font: normal medium/1.4 sans-serif;
}
div.greetblock, div.serverresponse {
  border-collapse: collapse;
  width: 60%;
  margin-left: auto;
  margin-right: auto;
  align: center;
}
tr > td {
  padding: 0.25rem;
  text-align: center;
  border: 1px solid #ccc;
}
tr:nth-child(even) {
  background: #fff;
 
}
tr:nth-child(odd) {
  background: #FA9A8B;
  color: #fff;
}
tr#header{
background: #F78371;
}
 
div#norecord{
margin-top:10px;
width: 15%;
margin-left: auto;
margin-right: auto;
}
input,select{
cursor: pointer;
}
img{
margin-top: 10px;
height: 200px;
width: 300px;
}
select{
width: 200px
}
div.leftdiv{
width: 45%;
padding: 0 10px;
float: left;
border: 1px solid #ccc;
margin: 5px;
height: 320px;
text-align:center;
}
div.rightdiv{
width: 45%;
padding: 0 10px;
float: right;
border: 1px solid #ccc;
margin: 5px;
height: 320px;
text-align:center;
}
div.centerdiv{
width: 90%;
padding: 0 10px;
float: center;
margin: 5px;
height: 320px;
text-align:center;
}
hidediv{
display: none;
}
p.header{
height: 40px;
background-color: #EB5038;
padding: 10px;
color: #fff;
text-align:center;
margin: 0;
margin-bottom: 10px;
}
textarea{
font-size: 25px;
font-weight: bold;
}
 
</style>
<script>
    function sendMsg(){
        
<?php
$target_dir = "promotions/";
$target_file = $target_dir . basename($_FILES["imageToUpload"]["name"]);
$uploadOk = 1;
$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
// Check if image file is a actual image or fake image
if(isset($_POST["submit"])) {
    $check = getimagesize($_FILES["imageToUpload"]["tmp_name"]);
    if($check !== false) {
        echo "File is an image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
}
?>

        var msgLength = $.trim($("textarea").val()).length;
        var checkedCB = $("input[type='checkbox']:checked").length;
        if(checkedCB == 0){
            alert("You must select at least one User to send message");
        }else if(msgLength == 0){
            alert("You left the message field blank, please fill it");
        }else{
            var formData = $(".wrapper").find("input").serialize()  + "&message=" + $("textarea").val();    
            $.ajax({type: "POST",data: formData, url: "secure/push.php", success:function(res){
                $(".greetblock").slideUp(1000);
                $(".serverresponse").prepend(res).hide().fadeIn(2000);
            }});
        }
    }
$(function(){
    $(".serverresponse").hide()
    $("input[type='checkbox']").click(function(){
        if($(this).is(':checked')){
            $(this).parent().css("border","3px solid red");
        }else{
            $(this).parent().css("border","0px");
        }
    });
 
    $("div.leftdiv, div.rightdiv").hover(function(){
        $(this).css("background","#FAFAFA");
    },function(){
        $(this).css("background","#fff");
    });
 
    $("#festival").change(function(){
        $("img").attr("src",$(this).val());
    });
 
    $("#sendmsg").click(function(){
        $(".serverresponse").fadeOut(300,function(){
            $(".greetblock").fadeIn(1000);
        });        
    });
});
</script>
</head>
<body>
<?php
    require ("secure/access.php");
	require ("secure/exxonconn.php");
	
	$access = new access(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	$access->connect();
    $users = $access->selectAllUsers("users");

    if ($users !=false){
        $no_of_users = mysqli_num_rows($users);
    }
    else{
        $no_of_users = 0;    
    }
?>
<?php
    if ($no_of_users > 0) {
?>
 
    <div class="greetblock">
        <div class="leftdiv">
        <p class="header">Select Users to whom you want to send PROMOTIONS</p>
        <table>
            <tr id="header"><td>Id</td><td>Name</td><td>Send Message?</td></tr>
            <?php
                while ($row = mysqli_fetch_array($users)) {
            ?> 
                    <tr>
                        <td><span><?php echo $row["id"] ?></span></td>
                        <td><span><?php echo $row["name"] ?></span></td>
                        <td><span class="wrapper"><input type="checkbox" name="sendmsg[]" value="<?php echo $row["regID"] ?>"/></span></td>
                    </tr>
            <?php } ?>
        </table>
    </div>
    <div class="rightdiv">
        <p class="header">Type your message</p>
        <textarea cols="15" rows="5" value="txtarea"></textarea>
        <input type="file" name="imageToUpload" id="imageToUpload">
    </div>
    <div class="centerdiv">
        <center>
            <button onclick="sendMsg()" name="submit">Send Message</button>
        </center>
    </div>
</div>
<div class="serverresponse hidediv">
    <center><button id="sendmsg">Send Message Again</button></center>
</div>
<?php }else{ ?>
<div id="norecord">
    No users in Database
</div>
<?php } ?>
 
</body>
</html>