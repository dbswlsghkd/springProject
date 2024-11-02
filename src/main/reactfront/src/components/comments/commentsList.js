import React, { useEffect } from 'react';

const CommentList = ({ comments, setComments }) => {
    const handleCommentDelete = (commentId) => {
        const url = `/api/comments/${commentId}`;
        fetch(url, {
            method: 'DELETE',
        })
            .then(response => {
                if (!response.ok) {
                    alert('댓글 삭제 실패..!');
                    return;
                }
                // 삭제 성공 시 댓글 목록에서 제거
                setComments(prevComments => prevComments.filter(comment => comment.id !== commentId));
            })
            .catch(error => {
                console.error('Error deleting comment:', error);
                alert('댓글 삭제 중 오류가 발생했습니다.');
            });
    };

    return (
        <div id="comments-list">
            {comments.map(comment => (
                <div className="card m-2" key={comment.id} id={`comments-${comment.id}`}>
                    <div className="card-header">
                        {comment.nickname}
                        <button
                            type="button"
                            className="btn btn-sm btn-outline-primary"
                            data-bs-toggle="modal"
                            data-bs-target="#comment-edit-modal"
                            data-bs-id={comment.id}
                            data-bs-nickname={comment.nickname}
                            data-bs-body={comment.body}
                            data-bs-article-id={comment.articleId}
                        >
                            수정
                        </button>
                        <button
                            type="button"
                            className="btn btn-sm btn-outline-danger comment-delete-btn"
                            onClick={() => handleCommentDelete(comment.id)}
                        >
                            삭제
                        </button>
                    </div>
                    <div className="card-body">
                        {comment.body}
                    </div>
                </div>
            ))}
            <CommentEditModal />
        </div>
    );
};

// 댓글 수정 모달 컴포넌트
const CommentEditModal = () => {
    const [editComment, setEditComment] = React.useState({
        id: '',
        nickname: '',
        body: '',
        articleId: '',
    });

    const handleModalShow = (event) => {
        const triggerBtn = event.relatedTarget;
        setEditComment({
            id: triggerBtn.getAttribute('data-bs-id'),
            nickname: triggerBtn.getAttribute('data-bs-nickname'),
            body: triggerBtn.getAttribute('data-bs-body'),
            articleId: triggerBtn.getAttribute('data-bs-article-id'),
        });
    };

    const handleCommentUpdate = () => {
        const url = `/api/comments/${editComment.id}`;
        fetch(url, {
            method: 'PATCH',
            body: JSON.stringify(editComment),
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                const msg = response.ok ? '댓글이 수정되었습니다.' : '댓글 수정 실패..!';
                alert(msg);
                // 수정 후, 모달 닫기 및 페이지 새로고침
                window.location.reload();
            })
            .catch(error => {
                console.error('Error updating comment:', error);
                alert('댓글 수정 중 오류가 발생했습니다.');
            });
    };

    return (
        <div className="modal fade" id="comment-edit-modal" tabIndex="-1" onShow={handleModalShow}>
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">댓글 수정</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={(e) => { e.preventDefault(); handleCommentUpdate(); }}>
                            <div className="mb-3">
                                <label className="form-label">닉네임</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={editComment.nickname}
                                    onChange={e => setEditComment({ ...editComment, nickname: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">댓글 내용</label>
                                <textarea
                                    className="form-control form-control-sm"
                                    rows="3"
                                    value={editComment.body}
                                    onChange={e => setEditComment({ ...editComment, body: e.target.value })}
                                    required
                                />
                            </div>
                            <input type="hidden" value={editComment.id} />
                            <input type="hidden" value={editComment.articleId} />
                            <button type="submit" className="btn btn-outline-primary btn-sm">수정 완료</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CommentList;
