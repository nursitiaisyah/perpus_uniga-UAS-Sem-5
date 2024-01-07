-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 07 Jan 2024 pada 05.28
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_perpusuniga`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_anggota`
--

CREATE TABLE `t_anggota` (
  `id_anggota` varchar(6) NOT NULL,
  `nim` varchar(20) NOT NULL,
  `nama_anggota` varchar(50) NOT NULL,
  `tmpt_lahir` varchar(50) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `alamat` text NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `jurusan` enum('Sistem Informasi','Ilmu Komunikasi','Akutansi') NOT NULL,
  `fakultas` enum('Teknik dan Informatika','Ekonomi dan Bisnis') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `t_anggota`
--

INSERT INTO `t_anggota` (`id_anggota`, `nim`, `nama_anggota`, `tmpt_lahir`, `tgl_lahir`, `jenis_kelamin`, `alamat`, `no_hp`, `jurusan`, `fakultas`) VALUES
('A00001', '21510024', 'Nur Siti Aisyah', 'Lumajang', '2002-10-15', 'Perempuan', 'Randuagung ', '081333641651', 'Sistem Informasi', 'Teknik dan Informatika'),
('A00002', '21510025', 'Feri', 'Kediri', '2024-01-01', 'Laki-Laki', 'Kediri', '123456654345', 'Sistem Informasi', 'Teknik dan Informatika'),
('A00003', '21510026', 'Ridwan', 'Surabaya', '2024-01-01', 'Laki-Laki', 'Surabaya', '089768567765', 'Ilmu Komunikasi', 'Teknik dan Informatika'),
('A00004', '21510027', 'Ari', 'Surabaya', '2024-01-04', 'Laki-Laki', 'Surabaya', '089768567453', 'Akutansi', 'Ekonomi dan Bisnis'),
('A00005', '21510028', 'Fita N', 'Malang', '1998-02-23', 'Perempuan', 'Sukun', '082331456234', 'Ilmu Komunikasi', 'Teknik dan Informatika');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_buku`
--

CREATE TABLE `t_buku` (
  `kode_buku` varchar(20) NOT NULL,
  `kode_registrasi` varchar(20) NOT NULL,
  `judul_buku` varchar(50) NOT NULL,
  `nama_pengarang` varchar(50) NOT NULL,
  `thn_terbit` varchar(20) NOT NULL,
  `tgl_pengadaan` date NOT NULL,
  `sumber_pengadaan` varchar(50) NOT NULL,
  `bahasa` varchar(50) NOT NULL,
  `status_ref` varchar(50) NOT NULL,
  `kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `t_buku`
--

INSERT INTO `t_buku` (`kode_buku`, `kode_registrasi`, `judul_buku`, `nama_pengarang`, `thn_terbit`, `tgl_pengadaan`, `sumber_pengadaan`, `bahasa`, `status_ref`, `kategori`) VALUES
('B00001', 'BR0001', 'Belajar Pemograman Python', 'Ridwan Abdilla', '2013', '2024-01-01', 'Sumbangan', 'Indonesia', 'Ya', 'Buku'),
('B00002', 'BR0002', 'Pancasila', 'Reyhandi', '2024', '2024-01-03', 'Kampus', 'Indonesia', 'Referensi', 'Buku');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_peminjaman`
--

CREATE TABLE `t_peminjaman` (
  `id_peminjaman` varchar(20) NOT NULL,
  `id_anggota` varchar(6) NOT NULL,
  `tgl_pinjam` date NOT NULL,
  `tgl_harap_kembali` date NOT NULL,
  `kode_buku` varchar(20) NOT NULL,
  `status_peminjaman` enum('Berlangsung','Selesai','Terlambat') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `t_peminjaman`
--

INSERT INTO `t_peminjaman` (`id_peminjaman`, `id_anggota`, `tgl_pinjam`, `tgl_harap_kembali`, `kode_buku`, `status_peminjaman`) VALUES
('M00001', 'A00004', '2024-01-01', '2024-01-10', 'B00002', 'Berlangsung'),
('M00002', 'A00002', '2024-01-01', '2024-01-08', 'B00001', 'Berlangsung'),
('M00003', 'A00002', '2024-01-02', '2024-01-08', 'B00001', 'Selesai');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_pengembalian`
--

CREATE TABLE `t_pengembalian` (
  `id_pengembalian` varchar(20) NOT NULL,
  `id_peminjaman` varchar(20) NOT NULL,
  `jenis_denda` enum('Tidak Ada','Rusak','Hilang') NOT NULL,
  `status_pengembalian` enum('Berlangsung','Selesai','Terlambat') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `t_pengembalian`
--

INSERT INTO `t_pengembalian` (`id_pengembalian`, `id_peminjaman`, `jenis_denda`, `status_pengembalian`) VALUES
('K00001', 'M00001', 'Tidak Ada', 'Selesai');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_petugas`
--

CREATE TABLE `t_petugas` (
  `id_petugas` varchar(6) NOT NULL,
  `nama_petugas` varchar(50) NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `alamat` text NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `jabatan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `t_petugas`
--

INSERT INTO `t_petugas` (`id_petugas`, `nama_petugas`, `tempat_lahir`, `tgl_lahir`, `jenis_kelamin`, `alamat`, `no_hp`, `email`, `jabatan`) VALUES
('P00001', 'Ahmad S.', 'Bandung', '1999-01-09', 'Laki-Laki', 'Bandung', '081333641634', 'ahmad2@gmail.com', 'Administrasi'),
('P00002', 'Aisyah', 'Lumajang', '2002-10-15', 'Perempuan', 'Randuagung', '081333641651', 'nur617859@gmail.com', 'Petugas');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`) VALUES
(1, 'aisyah', 'aisyah');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `t_anggota`
--
ALTER TABLE `t_anggota`
  ADD PRIMARY KEY (`id_anggota`);

--
-- Indeks untuk tabel `t_buku`
--
ALTER TABLE `t_buku`
  ADD PRIMARY KEY (`kode_buku`);

--
-- Indeks untuk tabel `t_peminjaman`
--
ALTER TABLE `t_peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_anggota` (`id_anggota`,`kode_buku`),
  ADD KEY `kode_buku` (`kode_buku`);

--
-- Indeks untuk tabel `t_pengembalian`
--
ALTER TABLE `t_pengembalian`
  ADD PRIMARY KEY (`id_pengembalian`),
  ADD KEY `id_peminjaman` (`id_peminjaman`);

--
-- Indeks untuk tabel `t_petugas`
--
ALTER TABLE `t_petugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `t_peminjaman`
--
ALTER TABLE `t_peminjaman`
  ADD CONSTRAINT `t_peminjaman_ibfk_1` FOREIGN KEY (`kode_buku`) REFERENCES `t_buku` (`kode_buku`),
  ADD CONSTRAINT `t_peminjaman_ibfk_2` FOREIGN KEY (`id_anggota`) REFERENCES `t_anggota` (`id_anggota`);

--
-- Ketidakleluasaan untuk tabel `t_pengembalian`
--
ALTER TABLE `t_pengembalian`
  ADD CONSTRAINT `t_pengembalian_ibfk_1` FOREIGN KEY (`id_peminjaman`) REFERENCES `t_peminjaman` (`id_peminjaman`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
