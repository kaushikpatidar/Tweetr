function populateTweets(responseDataArray){
    $(".even-twitter-element").remove();
    $(".odd-twitter-element").remove();
    $(".load-message-div").hide();

    $.each(responseDataArray, function(idx, responseData) {
        var $twitterElementDiv = $("<div></div>");
        var $twitterProfileElementDiv = $("<div></div>");
        var $twitterPostElementDiv = $("<div></div>");

        var $twitterPostElementDateDiv = $("<div></div>");
        var $twitterPostElementMessageDiv = $("<div></div>");

        var $twitterProfileImage = $("<img></img>");
        var $twitterHandleDiv = $("<div></div>");
        var $twitterUsernameDiv = $("<div></div>");

        $twitterProfileImage.attr("src", responseDataArray[idx].twitterUser.profileImageUrl);
        $twitterHandleDiv.html(responseDataArray[idx].twitterUser.twitterHandle);
        $twitterHandleDiv.addClass("div-bold");
        $twitterUsernameDiv.html(responseDataArray[idx].twitterUser.name);

        $twitterPostElementDateDiv.addClass("twitter-profile-element-date-div");
        $twitterPostElementDateDiv.html(new Date(responseDataArray[idx].createdAt).toDateString());
        $twitterPostElementMessageDiv.css("cursor", "pointer");
        $twitterPostElementMessageDiv.html(responseDataArray[idx].message);

        $twitterPostElementDiv.append($twitterPostElementDateDiv);
        $twitterPostElementDiv.append($twitterPostElementMessageDiv);

        $twitterElementDiv.on("click", function(){
            window.open('https://twitter.com/' + responseDataArray[idx].twitterUser.twitterHandle
                + '/status/'
                + responseDataArray[idx].twitterPostID,'newwindow');
        });

        $twitterProfileElementDiv.append($twitterProfileImage);
        $twitterProfileElementDiv.append($twitterHandleDiv);
        $twitterProfileElementDiv.append($twitterUsernameDiv);

        $twitterProfileElementDiv.addClass("twitter-profile-element-div");
        $twitterPostElementDiv.addClass("twitter-post-element-div");

        $twitterElementDiv.append($twitterProfileElementDiv);
        $twitterElementDiv.append($twitterPostElementDiv);

        if( idx % 2 != 0){
            $twitterElementDiv.addClass("odd-twitter-element");
        } else {
            $twitterElementDiv.addClass("even-twitter-element");
        }

        $(".tweet-container").append($twitterElementDiv);
    });

}

$(document).ready(function() {
    $("#loadmessagediv").show();

    $("#loadbutton").click(function(){
        $.ajax({
            url: 'http://localhost:8086/twitter/timeline/',
            success: function(responseData){
                populateTweets(responseData);
            }
        });
    });
});
