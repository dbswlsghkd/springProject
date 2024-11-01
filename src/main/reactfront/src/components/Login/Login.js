import React, { useState } from 'react';
import '../../styles/styles.css'; // 스타일 파일 추가

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault(); // 기본 폼 제출 방지
        const loginData = {
            username,
            psword: password,
        };

        try {
            const response = await fetch('/login', {
                method: 'POST',
                body: JSON.stringify(loginData),
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                alert('로그인 성공');
                window.location.href = '/articles'; // 성공 시 리다이렉트
            } else {
                alert('아이디와 비밀번호를 확인해주세요.');
                setUsername('');
                setPassword('');
            }
        } catch (error) {
            console.error('Error occurred:', error);
            alert('로그인 중 오류가 발생했습니다.');
        }
    };

    return (
        <div className="login-page">
            <div className="form">
                <form className="login-form" onSubmit={handleSubmit}>
                    <input
                        id="id"
                        name="username"
                        type="text"
                        placeholder="아이디"
                        maxLength="30"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <input
                        id="psword"
                        name="psword"
                        type="password"
                        placeholder="비밀번호"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit" id="button">Login</button>
                    <p className="message">
                        Not registered? <a href="/register">Create an account</a>
                    </p>
                </form>
            </div>
        </div>
    );
};

export default Login;
