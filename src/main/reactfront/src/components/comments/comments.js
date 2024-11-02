import React, { useEffect, useState } from 'react';
import CommentsList from './commentsList'; // 댓글 목록 컴포넌트
import NewComment from './commentsNew'; // 새 댓글 작성 컴포넌트

const Comments = ({ articleId }) => {
    const [comments, setComments] = useState([]); // 댓글 상태

    useEffect(() => {
        // 댓글 데이터 Fetch
        fetch(`/api/articles/${articleId}/comments`) // 실제 API 엔드포인트로 수정
            .then(response => response.json())
            .then(data => setComments(data))
            .catch(error => console.error('Error fetching comments:', error));
    }, [articleId]);

    return (
        <div>
            {/* 댓글 목록 */}
            <CommentsList comments={comments} />
            {/* 새 댓글 작성 */}
            <NewComment articleId={articleId} setComments={setComments} />
        </div>
    );
};

export default Comments;
