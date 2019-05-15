// li tag 생성
function make_comment_li(comment) {
    var content = comment['content'];
    var idx = comment['idx'];

    var p = document.createElement('p');
    p.className = "comment_content";
    p.appendChild(document.createTextNode(content));

    var btn_modify = document.createElement('button');
    btn_modify.className = "btn btn-primary btn_reply_modify";
    btn_modify.appendChild(document.createTextNode('수정'));
    btn_modify.setAttribute('data-is_editing', "false");

    var btn_delete = document.createElement('button');
    btn_delete.className = "btn btn-danger btn_reply_delete";
    btn_delete.appendChild(document.createTextNode('삭제'));

    var li = document.createElement('li');
    li.setAttribute('data-idx', idx.toString());
    li.append(p);
    li.append(btn_modify);
    li.append(btn_delete);

    return li;
}

// 댓글 추가
$('.list_insert').click(function () {
    var idx = $(this).data('idx');
    var text = $(this).parent().parent().find('.input_reply');
    var ul = $(this).parent().parent().parent().find('ul');
    var jsonData = JSON.stringify({
        content: text.val()
    });
    $.ajax({
        url: "http://localhost:8080/comment/" + idx,
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var li = make_comment_li(data);
            ul.append(li);
            text.val('');
            text.focus();
        },
        error: function () {
            alert('등록 실패!');
        }
    });
});

$(document).on('click', '.btn_reply_delete', function () {
    var li = $(this).parent();
    var idx = $(this).parent().data('idx');

    $.ajax({
        url: "http://localhost:8080/comment/" + idx,
        type: "DELETE",
        contentType: "application/json",
        dataType: "json",
        success: function () {
            li.remove();
        },
        error: function () {
            alert('삭제 실패!');
        }
    });
});

//댓글 수정
$(document).on('click', '.btn_reply_modify', function () {
    var idx = $(this).parent().data('idx');
    var content = $(this).parent().find('p');
    var is_editing = $(this).data('is_editing');
    var reply_input = $(this).parent().parent().parent().find('input_reply');
    if (!is_editing) {
        content.attr('contenteditable', 'true');
        content.focus();
        $(this).data('is_editing', true);
    } else {
        var jsonData = JSON.stringify({
            content: content.text()
        });
        $.ajax({
            url: "http://localhost:8080/comment/" + idx,
            type: "PUT",
            data: jsonData,
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                var text = data['content'];
                content.attr('contenteditable', 'false');
                content.text(text);
                reply_input.focus();
            },
            error: function () {
                alert('등록 실패!');
            }
        });
    }
});