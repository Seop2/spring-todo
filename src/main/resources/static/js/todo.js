// 댓글 확인
$('.btn_show_reply').click(function () {
    var list = $(this).parent().parent().parent().parent().find('.replies');
    list.slideToggle('slow');
    list.find('div').find('.input_reply').trigger('focus');
});

// todo 추가
$('#btn_add_todo').click(function () {
    var jsonData = JSON.stringify({
        description: $('#description').val()
    });
    $.ajax({
        url: "http://localhost:8080/todo",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('저장 실패!');
        }
    });
});

// todo 삭제
$('.btn_delete').click(function () {
    $.ajax({
        url: "http://localhost:8080/todo/" + $(this).parent().parent().parent().parent().data("idx"),
        type: "DELETE",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});

// todo 완료
$('.btn_complete').click(function () {
    $.ajax({
        url: "http://localhost:8080/todo/" + $(this).parent().parent().parent().parent().data("idx"),
        type: "PUT",
        contentType: "application/json",
        dataType: "json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert('수정 실패!');
        }
    });
});



// todo 수정
$('.btn_modify_description').click(function() {
    var is_editing = $(this).data('toggle');
    var description = $(this).parent().parent().find('.edit');
    var idx = $(this).parent().parent().parent().parent().data('idx');

    if (!is_editing) {
        description.attr('contenteditable', 'true');
        description.focus();
        $(this).siblings().css({'display': 'none'});
        $(this).data('toggle', true);
    } else {
        var jsonData = JSON.stringify({
            description: description.text()
        });
        $.ajax({
            url: "http://localhost:8080/todo/edit/" + idx,
            type: "PUT",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function () {
                location.reload();
            },
            error: function () {
                alert('수정 실패!');
            }
        });
    }
});