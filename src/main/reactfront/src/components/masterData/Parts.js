import React, { useState, useEffect } from "react";
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

function PartManagement() {
    const [parts, setParts] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState("");
    const [modalData, setModalData] = useState({ part_code: "", part_name: "", part_std: "" });
    const [isCreateModalOpen, setCreateModalOpen] = useState(false);
    const [isUpdateModalOpen, setUpdateModalOpen] = useState(false);

    const itemsPerPage = 15;

    useEffect(() => {
        fetchParts(searchTerm, currentPage);
    }, [searchTerm, currentPage]);

    const fetchParts = async (searchTerm, page) => {
        try {
            const response = await fetch(`/api/parts?search=${encodeURIComponent(searchTerm)}&page=${page}&size=${itemsPerPage}`);
            const data = await response.json();
            setParts(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching parts:", error);
        }
    };

    const handleSearch = () => {
        setCurrentPage(0); // 검색 시 첫 페이지로 초기화
        fetchParts(searchTerm, 0);
    };

    const handleCreatePart = async () => {
        const url = "/api/part/create";
        try {
            const response = await fetch(url, {
                method: "POST",
                body: JSON.stringify(modalData),
                headers: { "Content-Type": "application/json" },
            });
            if (response.ok) {
                setCreateModalOpen(false);
                fetchParts(searchTerm, currentPage); // 페이지 새로고침
            } else {
                alert("품번 등록 실패하였습니다.!");
            }
        } catch (error) {
            console.error("Error creating part:", error);
        }
    };

    const handleUpdatePart = async () => {
        const url = `/api/part/update/${encodeURIComponent(modalData.part_code)}`;
        try {
            const response = await fetch(url, {
                method: "PATCH",
                body: JSON.stringify(modalData),
                headers: { "Content-Type": "application/json" },
            });
            if (response.ok) {
                alert("품번이 수정되었습니다.");
                setUpdateModalOpen(false);
                fetchParts(searchTerm, currentPage);
            } else {
                alert("품번 수정 실패..!");
            }
        } catch (error) {
            console.error("Error updating part:", error);
        }
    };

    const openCreateModal = () => {
        setModalData({ part_code: "", part_name: "", part_std: "" });
        setCreateModalOpen(true);
    };

    const openUpdateModal = (part) => {
        setModalData(part);
        setUpdateModalOpen(true);
    };

    return (
        <>
            <Header>
                <div>
                    <div className="input-group ms-auto">
                        <button type="button" className="btn btn-sm btn-outline-dark" onClick={openCreateModal}>
                            등록
                        </button>
                    </div>
                    <div className="input-group mb-3 w-25 ms-auto">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="품번, 품명, 규격"
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
                            <th style={{width: "150px", textAlign: "center"}}>품번</th>
                            <th style={{width: "150px", textAlign: "center"}}>품명</th>
                            <th style={{width: "150px", textAlign: "center"}}>규격</th>
                        </tr>
                        </thead>
                        <tbody>
                        {parts.map((part) => (
                            <tr key={part.part_code} onClick={() => openUpdateModal(part)}>
                                <th>{part.part_code}</th>
                                <td>{part.part_name}</td>
                                <td>{part.part_std}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <Pagination totalPages={totalPages} currentPage={currentPage} setCurrentPage={setCurrentPage} />

                    {/* Create Modal */}
                    {isCreateModalOpen && (
                        <Modal title="품번 등록" modalData={modalData} setModalData={setModalData}
                               onSubmit={handleCreatePart} onClose={() => setCreateModalOpen(false)}/>
                    )}

                    {/* Update Modal */}
                    {isUpdateModalOpen && (
                        <Modal title="품번 수정" modalData={modalData} setModalData={setModalData}
                               onSubmit={handleUpdatePart} onClose={() => setUpdateModalOpen(false)}/>
                    )}
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

function Modal({ title, modalData, setModalData, onSubmit, onClose }) {
    return (
        <div className="modal show d-block" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                        <button type="button" className="btn-close" onClick={onClose}></button>
                    </div>
                    <div className="modal-body">
                        <form>
                            <div className="mb-3">
                                <label className="form-label">품번</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.part_code}
                                    onChange={(e) => setModalData({ ...modalData, part_code: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">품명</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.part_name}
                                    onChange={(e) => setModalData({ ...modalData, part_name: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">규격</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.part_std}
                                    onChange={(e) => setModalData({ ...modalData, part_std: e.target.value })}
                                />
                            </div>
                            <button type="button" className="btn btn-outline-dark btn-sm" onClick={onSubmit}>등록 완료</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PartManagement;
