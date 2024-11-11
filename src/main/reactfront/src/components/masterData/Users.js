import React, { useState, useEffect } from "react";
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

function UserManagement() {
    const [users, setUsers] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState("");

    const itemsPerPage = 15;

    useEffect(() => {
        fetchUsers(searchTerm, currentPage);
    }, [searchTerm, currentPage]);

    const fetchUsers = async (searchTerm, page) => {
        try {
            const response = await fetch(`/api/users?search=${encodeURIComponent(searchTerm)}&page=${page}&size=${itemsPerPage}`);
            const data = await response.json();
            setUsers(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    };

    const handleSearch = () => {
        setCurrentPage(0); // 검색 시 첫 페이지로 초기화
        fetchUsers(searchTerm, 0);
    };

    return (
        <>
            <Header>
                <div>
                    <div className="input-group mb-3 w-25 ms-auto">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="사용자ID, 사용자이름, ROLE"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                        <button className="btn btn-outline-secondary" onClick={handleSearch}>
                            <i className="bi bi-search"></i>
                        </button>
                    </div>

                    <table className="table table-striped table-sm table-hover">
                        <thead className="table-dark">
                        <tr>
                            <th style={{width: "150px", textAlign: "center"}}>사용자 ID</th>
                            <th style={{width: "150px", textAlign: "center"}}>사용자 이름</th>
                            <th style={{width: "150px", textAlign: "center"}}>ROLE</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.map((users) => (
                            <tr key={users.userid}>
                                <th>{users.userid}</th>
                                <td>{users.name}</td>
                                <td>{users.role}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <Pagination totalPages={totalPages} currentPage={currentPage} setCurrentPage={setCurrentPage} />
                </div>
            </Header>
            <Footer/>
        </>
    );
}

const Pagination = ({ totalPages, currentPage, setCurrentPage }) => {
    const maxPagesToShow = 10; // 최대 표시할 페이지 수
    const startPage = Math.max(0, currentPage - Math.floor(maxPagesToShow / 2));
    const endPage = Math.min(totalPages, startPage + maxPagesToShow);

    const pages = Array.from({ length: endPage - startPage }, (_, i) => startPage + i);

    return (
        <nav aria-label="Page navigation">
            <ul className="pagination justify-content-center">
                <li className={`page-item ${currentPage === 0 ? 'disabled' : ''}`}>
                    <button className="page-link" onClick={() => setCurrentPage(currentPage - 1)}>&laquo;</button>
                </li>
                {pages.map((page) => (
                    <li key={page} className={`page-item ${page === currentPage ? 'active' : ''}`}>
                        <button className="page-link" onClick={() => setCurrentPage(page)}>{page + 1}</button>
                    </li>
                ))}
                <li className={`page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}`}>
                    <button className="page-link"  onClick={() => setCurrentPage(currentPage + 1)}>&raquo;</button>
                </li>
            </ul>
        </nav>
    );
};

export default UserManagement;
