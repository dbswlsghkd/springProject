// src/components/Footer.js
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import '../../styles/styles.css'; // 스타일 파일 추가

function Footer() {
    return (
        <footer className="bg-dark text-white p-4 mt-4">
            <div className="container text-center">
                <p>&copy; 2024 HWANGYOONJIN Website. All rights reserved.</p>
            </div>
        </footer>
    );
}

export default Footer;
