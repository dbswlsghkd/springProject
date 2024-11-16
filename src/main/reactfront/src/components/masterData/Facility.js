import React, { useState, useEffect } from "react";
import Header from '../layouts/Header';
import Footer from '../layouts/Footer';

function FacilityManagement() {
    const [facilities, setFacility] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState("");
    const [modalData, setModalData] = useState({ facility_code: "", facility_name: ""});
    const [isCreateModalOpen, setCreateModalOpen] = useState(false);
    const [isUpdateModalOpen, setUpdateModalOpen] = useState(false);

    const itemsPerPage = 15;

    useEffect(() => {
        fetchFacility(searchTerm, currentPage);
    }, [searchTerm, currentPage]);

    const fetchFacility = async (searchTerm, page) => {
        try {
            const response = await fetch(`/api/facility?search=${encodeURIComponent(searchTerm)}&page=${page}&size=${itemsPerPage}`);
            const data = await response.json();
            console.log(data);
            setFacility(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching facility:", error);
        }
    };

    const handleSearch = () => {
        setCurrentPage(0); // 검색 시 첫 페이지로 초기화
        fetchFacility(searchTerm, 0);
    };

    const handleCreateModel = async () => {
        const url = "/api/facility/create";
        try {
            const response = await fetch(url, {
                method: "POST",
                body: JSON.stringify(modalData),
                headers: { "Content-Type": "application/json" },
            });
            if (response.ok) {
                setCreateModalOpen(false);
                fetchFacility(searchTerm, currentPage); // 페이지 새로고침
            } else {
                alert("설비 등록 실패하였습니다.!");
            }
        } catch (error) {
            console.error("Error creating part:", error);
        }
    };

    const handleUpdateModel = async () => {
        const url = `/api/facility/update/${encodeURIComponent(modalData.facility_code)}`;
        try {
            const response = await fetch(url, {
                method: "PATCH",
                body: JSON.stringify(modalData),
                headers: { "Content-Type": "application/json" },
            });
            if (response.ok) {
                alert("설비 수정되었습니다.");
                setUpdateModalOpen(false);
                fetchFacility(searchTerm, currentPage);
            } else {
                alert("설비 수정 실패..!");
            }
        } catch (error) {
            console.error("Error updating part:", error);
        }
    };

    const openCreateModal = () => {
        setModalData({ facility_code: "", facility_name: ""});
        setCreateModalOpen(true);
    };

    const openUpdateModal = (facility) => {
        setModalData(facility);
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
                            placeholder="설비코드, 설비명"
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
                            <th style={{width: "150px", textAlign: "center"}}>설비코드</th>
                            <th style={{width: "150px", textAlign: "center"}}>설비명</th>
                            <th style={{width: "150px", textAlign: "center"}}>등록일자</th>
                        </tr>
                        </thead>
                        <tbody>
                        {facilities.map((facility) => (
                            <tr key={facility.facility_code} onClick={() => openUpdateModal(facility)}>
                                <th>{facility.facility_code}</th>
                                <td>{facility.facility_name}</td>
                                <td>{facility.regdt}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <Pagination totalPages={totalPages} currentPage={currentPage} setCurrentPage={setCurrentPage} />

                    {/* Create Modal */}
                    {isCreateModalOpen && (
                        <Modal title="설비 등록" modalData={modalData} setModalData={setModalData}
                               onSubmit={handleCreateModel} onClose={() => setCreateModalOpen(false)}/>
                    )}

                    {/* Update Modal */}
                    {isUpdateModalOpen && (
                        <Modal title="설비 수정" modalData={modalData} setModalData={setModalData}
                               onSubmit={handleUpdateModel} onClose={() => setUpdateModalOpen(false)}/>
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
                                <label className="form-label">설비코드</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.facility_code}
                                    onChange={(e) => setModalData({ ...modalData, facility_code: e.target.value })}
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">설비명</label>
                                <input
                                    type="text"
                                    className="form-control form-control-sm"
                                    value={modalData.facility_name}
                                    onChange={(e) => setModalData({ ...modalData, facility_name: e.target.value })}
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

export default FacilityManagement;
