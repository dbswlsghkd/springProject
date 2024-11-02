import React, { useState } from 'react';
import { Link, useNavigate  } from 'react-router-dom';
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

const CreateArticle = () => {
    const [title, setTitle] = useState(''); // 제목 상태
    const [content, setContent] = useState(''); // 내용 상태
    const navigate  = useNavigate ();

    const handleSubmit = (e) => {
        e.preventDefault(); // 기본 폼 제출 동작 방지

        // 기사 생성 API 요청
        fetch('/articles/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title, content }), // JSON 형식으로 데이터 전송
        })
            .then(response => {
                if (response.ok) {
                    // 생성 성공 시 기사 목록 페이지로 리디렉션
                    navigate('/articles');
                } else {
                    console.error('Failed to create article');
                }
            })
            .catch(error => console.error('Error creating article:', error));
    };

    return (
        <>
            <Header>
            <form className="container" onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">제목</label>
                    <input
                        type="text"
                        className="form-control"
                        name="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)} // 제목 변경 시 상태 업데이트
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">내용</label>
                    <textarea
                        className="form-control"
                        rows="3"
                        name="content"
                        value={content}
                        onChange={(e) => setContent(e.target.value)} // 내용 변경 시 상태 업데이트
                    ></textarea>
                </div>
                <button type="submit" className="btn btn-primary mb-3">Submit</button>
                <Link to="/articles" className="btn btn-dark mb-3">Back</Link>
            </form>
            </Header>
            <Footer />
        </>
    );
};

export default CreateArticle;
