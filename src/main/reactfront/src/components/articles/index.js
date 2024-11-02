// src/components/Articles.js
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

function Articles() {
    const [articleList, setArticleList] = useState([]);

    useEffect(() => {
        // Fetch articles data from the server or API endpoint
        fetch('/api/articles') // Replace with your actual API endpoint
            .then(response => response.json())
            .then(data => setArticleList(data))
            .catch(error => console.error('Error fetching articles:', error));
    }, []);

    return (
        <>
            <Header>
                <div className="container my-3">
                    <Link className="btn btn-dark mb-3" to="/articles/new">
                        글쓰기
                    </Link>
                    <table className="table table-striped table-sm">
                        <thead className="table-dark">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Title</th>
                            <th scope="col">Content</th>
                            <th scope="col">등록일자</th>
                        </tr>
                        </thead>
                        <tbody>
                        {articleList.map(article => (
                            <tr key={article.id}>
                                <th>{article.id}</th>
                                <td>
                                    <Link to={`/articles/${article.id}`}>{article.title}</Link>
                                </td>
                                <td>{article.content}</td>
                                <td>{article.regdt}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </Header>
            <Footer />
        </>
    );
}

export default Articles;
