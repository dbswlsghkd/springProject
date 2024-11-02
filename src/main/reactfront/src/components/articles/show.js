import React, { useEffect, useState } from 'react';
import { Link, useParams, useHistory } from 'react-router-dom';
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';
import Comments from '../comments/Comments'; // 댓글 컴포넌트 import

const ArticleDetail = () => {
    const { id } = useParams(); // URL에서 ID 파라미터 가져오기
    const [article, setArticle] = useState(null); // 기사 상태
    const history = useHistory(); // history 객체로 리디렉션 처리

    useEffect(() => {
        // 기사 데이터 Fetch
        fetch(`/api/articles/${id}`) // 실제 API 엔드포인트로 수정
            .then(response => response.json())
            .then(data => setArticle(data))
            .catch(error => console.error('Error fetching article:', error));
    }, [id]);

    const handleDelete = () => {
        // 기사 삭제 처리
        fetch(`/api/articles/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    // 삭제 성공 시 목록 페이지로 리디렉션
                    history.push('/articles');
                } else {
                    console.error('Failed to delete article');
                }
            })
            .catch(error => console.error('Error deleting article:', error));
    };

    if (!article) return <div>Loading...</div>; // 기사 데이터가 로딩 중일 경우

    return (
        <>
            <Header />
            {/* 수정 링크 */}
            <Link to={`/articles/${article.id}/edit`} className="btn btn-primary mb-3">Edit</Link>
            {/* 삭제 링크 */}
            <button onClick={handleDelete} className="btn btn-danger mb-3">Delete</button>
            <Link to="/articles" className="btn btn-dark mb-3">Go to Article List</Link>

            <table className="table">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Title</th>
                    <th scope="col">Content</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th>{article.id}</th>
                    <td>{article.title}</td>
                    <td>{article.content}</td>
                </tr>
                </tbody>
            </table>

            {/* 댓글 컴포넌트 삽입 */}
            <Comments articleId={article.id} />
            <Footer />
        </>
    );
};

export default ArticleDetail;