$("#book-select").change(function() {
    var x = $(this).val();
    if(x == 0) {
        $("#book-overlay").show();
    }
    else {
        var s = "";
        for(var i = 0; i < chapters.length; i++) {
            s = s + "<p> Chapter " + chapters[i].chapter_index + " : " + chapters[i].chapter_name + "</p>";
        }

        $("#chapters-div").html(s);
    }
});

$("#chapter-select").change(function() {
    var x = $(this).val();
    if(x == 0) {
        $("#chapter-overlay").show();
    }
});

$(".overlay").click(function(e) {
    $(this).hide();
});

$(".create-book-form").click(function(e) {
    e.stopPropagation();
});

$(".chapter-heading").click(function() {
    var index = $(this).data("index");
    $("#topics-" + index).slideToggle();
});

var chapters = [
    {
        "chapter_name" : "Motion",
        "chapter_index" : 1,
        "topics" : [
            {
                "topic_name" : "Introduction",
                "topic_index" : 1
            },
            {
                "topic_name" : "Topic 2",
                "topic_index" : 2
            },
            {
                "topic_name" : "Topic 3",
                "topic_index" : 3
            }
        ]
    },
    {
        "chapter_name" : "Name Two",
        "chapter_index" : 2,
        "topics" : [
            {
                "topic_name" : "Introduction",
                "topic_index" : 1
            },
            {
                "topic_name" : "Topic 2",
                "topic_index" : 2
            },
            {
                "topic_name" : "Topic 3",
                "topic_index" : 3
            }
        ]
    },
    {
        "chapter_name" : "Name Three",
        "chapter_index" : 3,
        "topics" : [
            {
                "topic_name" : "Introduction",
                "topic_index" : 1
            },
            {
                "topic_name" : "Topic 2",
                "topic_index" : 2
            },
            {
                "topic_name" : "Topic 3",
                "topic_index" : 3
            }
        ]
    }
]