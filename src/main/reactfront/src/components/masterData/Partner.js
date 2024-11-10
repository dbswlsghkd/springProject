import React, { useState, useEffect } from "react";
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

function PartnersManagement() {
    const [partners, setPartners] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState("");
    const [modalData, setModalData] = useState({ partner_code: "", partner_name: "", partner_address: "" });
    const [isCreateModalOpen, setCreateModalOpen] = useState(false);
    const [isUpdateModalOpen, setUpdateModalOpen] = useState(false);

    const itemsPerPage = 15;

    useEffect(() => {
        fetchPartners(searchTerm, currentPage);
    }, [searchTerm, currentPage]);

    const fetchPartners = async (searchTerm, page) => {
        try {
            const response = await fetch(`/api/partners?search=${encodeURIComponent(searchTerm)}&page=${page}&size=${itemsPerPage}`);
            const data = await response.json();
            setPartners(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching Partners:", error);
        }
    };

    const handleSearch = () => {
        setCurrentPage(0); // 검색 시 첫 페이지로 초기화
        fetchPartners(searchTerm, 0);
    };

    const handleCreatePart = async () => {
        const url = "/api/partners/create";
        try {
            const response = await fetch(url, {
                method: "POST",
                body: JSON.stringify(modalData),
                headers: { "Content-Type": "application/json" },
            });
            if (response.ok) {
                setCreateModalOpen(false);
                fetchPartners(searchTerm, currentPage); // 페이지 새로고침
            } else {
                alert("거래처 등록 실패하였습니다.!");
            }
        } catch (error) {
            console.error("Error creating partners:", error);
        }
    };

    const handleUpdatePart = async () => {
        const url = `/api/partners/update/${encodeURIComponent(modalData.partner_code)}`;
        try {
            const response = await fetch(url, {
                method: "PATCH",
                body: JSON.stringify(modalData),
                headers: { "Content-Type": "application/json" },
            });
            if (response.ok) {
                alert("거래처 수정되었습니다.");
                setUpdateModalOpen(false);
                fetchPartners(searchTerm, currentPage);
            } else {
                alert("거래처 수정 실패..!");
            }
        } catch (error) {
            console.error("Error updating part:", error);
        }
    };

    const openCreateModal = () => {
        setModalData({ partner_code: "", partner_name: "", partner_address: "" });
        setCreateModalOpen(true);
    };

    const openUpdateModal = (partners) => {
        setModalData(partners);
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
                            placeholder="거래처, 거래처명, 주소"
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
                            <th style={{width: "50px", textAlign: "center"}}>거래처 코드</th>
                            <th style={{width: "150px", textAlign: "center"}}>거래처명</th>
                            <th style={{width: "250px", textAlign: "center"}}>주소</th>
                        </tr>
                        </thead>
                        <tbody>
                        {partners.map((partners) => (
                            <tr key={partners.partner_code} onClick={() => openUpdateModal(partners)}>
                                <th>{partners.partner_code}</th>
                                <td>{partners.partner_name}</td>
                                <td>{partners.partner_address}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <Pagination totalPages={totalPages} currentPage={currentPage} setCurrentPage={setCurrentPage} />

                    {/* Create Modal */}
                    {isCreateModalOpen && (
                        <Modal title="거래처 등록" modalData={modalData} setModalData={setModalData}
                               onSubmit={handleCreatePart} onClose={() => setCreateModalOpen(false)}/>
                    )}

                    {/* Update Modal */}
                    {isUpdateModalOpen && (
                        <Modal title="거래처 수정" modalData={modalData} setModalData={setModalData}
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
                                <label className="form-label">거래처 코드</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.partner_code}
                                    onChange={(e) => setModalData({ ...modalData, partner_code: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">거래처명</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.partner_name}
                                    onChange={(e) => setModalData({ ...modalData, partner_name: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">주소</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.partner_address}
                                    onChange={(e) => setModalData({ ...modalData, partner_address: e.target.value })}
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

export default PartnersManagement;
