import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/styles.css'; // 스타일 파일 추가

function RegisterForm() {
    const [formData, setFormData] = useState({
        userid: '',
        name: '',
        psword: '',
        confirmPsword: ''
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormData({
            ...formData,
            [id]: value
        });
    };

    const handleRegister = async () => {
        const { userid, name, psword, confirmPsword } = formData;

        if (psword !== confirmPsword) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        const registerData = { userid, name, psword };

        try {
            const response = await fetch('/api/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(registerData)
            });

            if (response.ok) {
                navigate('/login');
            } else {
                alert('회원가입 실패하였습니다!');
            }
        } catch (error) {
            console.error('Error occurred:', error);
        }
    };

    return (
        <div className="login-page">
            <div className="form">
                <form className="login-form" onSubmit={(e) => e.preventDefault()}>
                    <div className="id-check-container">
                        <input
                            id="userid"
                            className="register-id"
                            type="text"
                            placeholder="아이디"
                            maxLength="30"
                            value={formData.userid}
                            onChange={handleChange}
                        />
                        <p id="duplicate-check">중복체크</p>
                    </div>
                    <input
                        id="name"
                        type="text"
                        placeholder="이름"
                        value={formData.name}
                        onChange={handleChange}
                    />
                    <input
                        id="psword"
                        type="password"
                        placeholder="비밀번호"
                        value={formData.psword}
                        onChange={handleChange}
                    />
                    <input
                        id="confirmPsword"
                        type="password"
                        placeholder="비밀번호 확인"
                        value={formData.confirmPsword}
                        onChange={handleChange}
                    />
                    <button type="button" id="button" onClick={handleRegister}>
                        SIGN UP
                    </button>
                    <p className="message">
                        Already registered? <a href="/login">login</a>
                    </p>
                </form>
            </div>
        </div>
    );
}

export default RegisterForm;
