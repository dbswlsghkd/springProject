import React, { useState } from 'react';

const NewComment = ({ articleId, setComments }) => {
    const [nickname, setNickname] = useState(''); // 닉네임 상태
    const [body, setBody] = useState(''); // 댓글 내용 상태

    const handleCommentCreate = () => {
        const comment = {
            nickname,
            body,
            article_id: articleId,
        };

        // 댓글 생성 API 요청
        const url = `/api/articles/${articleId}/comments`;
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(comment),
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                const msg = response.ok ? '댓글이 등록되었습니다.' : '댓글 등록 실패..!';
                alert(msg);
                if (response.ok) {
                    // 댓글이 성공적으로 등록되면 상태 업데이트
                    setComments(prevComments => [...prevComments, comment]);
                    setNickname(''); // 닉네임 필드 초기화
                    setBody(''); // 댓글 본문 필드 초기화
                }
            })
            .catch(error => {
                console.error('Error creating comment:', error);
                alert('댓글 등록 중 오류가 발생했습니다.');
            });
    };

    return (
        <div className="card m-2" id="comments-new">
            <div className="card-body">
                {/* 댓글 작성 폼 */}
                <form onSubmit={e => e.preventDefault()}>
                    {/* 닉네임 입력 */}
                    <div className="mb-3">
                        <label className="form-label">닉네임</label>
                        <input
                            type="text"
                            className="form-control form-control-sm"
                            value={nickname}
                            onChange={e => setNickname(e.target.value)}
                            required
                        />
                    </div>
                    {/* 댓글 본문 입력 */}
                    <div className="mb-3">
                        <label className="form-label">댓글 내용</label>
                        <textarea
                            className="form-control form-control-sm"
                            rows="3"
                            value={body}
                            onChange={e => setBody(e.target.value)}
                            required
                        />
                    </div>
                    {/* 히든 인풋 */}
                    <input type="hidden" value={articleId} />
                    {/* 전송 버튼 */}
                    <button type="button" className="btn btn-outline-primary btn-sm" onClick={handleCommentCreate}>
                        댓글 작성
                    </button>
                </form>
            </div>
        </div>
    );
};

export default NewComment;
