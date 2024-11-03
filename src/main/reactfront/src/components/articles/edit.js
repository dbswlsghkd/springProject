import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate} from 'react-router-dom';
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

const EditArticle = () => {
    const { id } = useParams(); // URL에서 id 가져오기
    const [article, setArticle] = useState(null); // 상태 관리
    const navigate = useNavigate();

    useEffect(() => {
        // 특정 기사를 가져오기 위한 API 요청
        fetch(`/articles/${id}/edit`)
            .then(response => {
                console.log("Fetch response:", response); // 응답 상태 확인
                if (!response.ok) throw new Error("Failed to fetch article");
                return response.json();
            })
            .then(data => {
                console.log("Article data:", data); // 데이터 확인
                setArticle(data);
            })
            .catch(error => console.error('Error fetching article:', error));
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault(); // 기본 폼 제출 동작 방지
        const articleData = {
            id: article.id,
            title: e.target.title.value,
            content: e.target.content.value,
        };

        // 기사 업데이트 API 요청
        fetch('/articles/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(articleData),
        })
            .then(response => {
                if (response.ok) {
                    // 업데이트 성공 시 리디렉션 또는 처리
                    navigate(`/articles/${id}`); // 수정된 기사 페이지로 리디렉션
                } else {
                    console.error('Failed to update article');
                }
            })
            .catch(error => console.error('Error updating article:', error));
    };

    if (!article) {
        return <div>Loading...</div>; // 기사를 로딩 중일 때
    }

    return (
        <>
            <Header>
            <form className="container" onSubmit={handleSubmit}>
                <input name="id" type="hidden" value={article.id} />
                <div className="mb-3">
                    <label className="form-label">제목</label>
                    <input
                        type="text"
                        className="form-control"
                        name="title"
                        defaultValue={article.title} // 기본값으로 기사 제목 설정
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">내용</label>
                    <textarea
                        className="form-control"
                        rows="3"
                        name="content"
                        defaultValue={article.content} // 기본값으로 기사 내용 설정
                    ></textarea>
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
                <Link to={`/articles/${article.id}`} className="btn btn-secondary ms-2">Back</Link>
            </form>
            </Header>
            <Footer />
        </>
    );
};

export default EditArticle;
