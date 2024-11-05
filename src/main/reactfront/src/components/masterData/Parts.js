import React, { useState, useEffect } from 'react';
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';
import 'bootstrap/dist/css/bootstrap.min.css';

const PartTable = () => {
    const [parts, setParts] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [selectedPart, setSelectedPart] = useState(null);

    const itemsPerPage = 15;

    useEffect(() => {
        fetchParts(searchTerm, currentPage);
    }, [currentPage, searchTerm]);

    const fetchParts = async (term, page) => {
        try {
            const response = await fetch(`/api/parts?search=${encodeURIComponent(term)}&page=${page}&size=${itemsPerPage}`);
            const data = await response.json();
            setParts(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error('Error fetching parts:', error);
        }
    };

    const handleSearch = () => {
        setCurrentPage(0); // 검색 시 첫 페이지로 이동
        fetchParts(searchTerm, 0);
    };

    const handleCreatePart = async (part) => {
        try {
            const response = await fetch('/api/part/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(part),
            });
            if (response.ok) {
                fetchParts(searchTerm, currentPage);
            } else {
                alert('품번 등록 실패하였습니다.');
            }
        } catch (error) {
            console.error('Error creating part:', error);
        }
    };

    const handleUpdatePart = async (part) => {
        try {
            const response = await fetch(`/api/part/update/${encodeURIComponent(part.part_code)}`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(part),
            });
            if (response.ok) {
                alert('품번이 수정 되었습니다.');
                fetchParts(searchTerm, currentPage);
            } else {
                alert('품번 수정 실패..!');
            }
        } catch (error) {
            console.error('Error updating part:', error);
        }
    };

    return (
        <>
        <Header>
        <div>
            <div className="input-group ms-auto">
                <button type="button" className="btn btn-sm btn-outline-dark" data-bs-toggle="modal" data-bs-target="#comment-create-modal">
                    등록
                </button>
            </div>

            <div className="input-group mb-3 w-25 ms-auto">
                <input
                    id="searchInput"
                    type="text"
                    className="form-control"
                    placeholder="품번, 품명, 규격"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
                <button className="btn btn-outline-secondary" type="button" onClick={handleSearch}>
                    <i className="bi bi-search"></i>
                </button>
            </div>

            <table className="table table-striped table-sm table-hover">
                <thead className="table-dark">
                <tr>
                    <th style={{ width: '150px', textAlign: 'center' }}>품번</th>
                    <th style={{ width: '150px', textAlign: 'center' }}>품명</th>
                    <th style={{ width: '150px', textAlign: 'center' }}>규격</th>
                </tr>
                </thead>
                <tbody>
                {parts.map((part) => (
                    <tr
                        key={part.part_code}
                        data-bs-toggle="modal"
                        data-bs-target="#comment-update-modal"
                        onClick={() => setSelectedPart(part)}
                    >
                        <th>{part.part_code}</th>
                        <td>{part.part_name}</td>
                        <td>{part.part_std}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <Pagination totalPages={totalPages} currentPage={currentPage} setCurrentPage={setCurrentPage} />
            <PartFormModal onCreate={handleCreatePart} />
            {selectedPart && <CommentUpdateModal part={selectedPart} onUpdate={handleUpdatePart} />}
        </div>
        </Header>
        <Footer />
        </>
    );
};

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

const PartFormModal = ({onCreate}) => {
    const [part, setPart] = useState({ part_code: '', part_name: '', part_std: '' });

    const handleSubmit = () => {
        onCreate(part);
        setPart({ part_code: '', part_name: '', part_std: '' });
    };

    return (
        <div className="modal fade" id="comment-create-modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">품번 등록</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        <form>
                            <div className="mb-3">
                                <label className="form-label">품번</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={part.part_code}
                                    onChange={(e) => setPart({ ...part, part_code: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">품명</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={part.part_name}
                                    onChange={(e) => setPart({ ...part, part_name: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">규격</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={part.part_std}
                                    onChange={(e) => setPart({ ...part, part_std: e.target.value })}
                                />
                            </div>
                            <button type="button" className="btn btn-outline-dark btn-sm" onClick={handleSubmit}>
                                등록 완료
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

const CommentUpdateModal = ({ part, onUpdate }) => {
    const [updatedPart, setUpdatedPart] = useState(part);

    const handleUpdate = () => {
        onUpdate(updatedPart);
    };

    return (
        <div className="modal fade" id="comment-update-modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">품번 수정</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        <form>
                            <div className="mb-3">
                                <label className="form-label">품번</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={updatedPart.part_code}
                                    onChange={(e) => setUpdatedPart({ ...updatedPart, part_code: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">품명</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={updatedPart.part_name}
                                    onChange={(e) => setUpdatedPart({ ...updatedPart, part_name: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">규격</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={updatedPart.part_std}
                                    onChange={(e) => setUpdatedPart({ ...updatedPart, part_std: e.target.value })}
                                />
                            </div>
                            <button type="button" className="btn btn-outline-dark btn-sm" onClick={handleUpdate}>
                                수정 완료
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PartTable;
