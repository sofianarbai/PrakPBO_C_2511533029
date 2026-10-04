package pekan4;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);

		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif = null; // objek belum diinisialisasi
		boolean isRunning = true;

		System.out.println("==== SISTEM PERBANKAN MINI ===");

		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat)");
			System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
			System.out.println("0. Keluar");
			System.out.print("Pilih Menu: ");

			if (!input.hasNextInt()) {
				System.out.println("Error: Pilihan menu harus berupa angka!");
				input.next();
				continue;
			}

			int pilihan = input.nextInt();
			input.nextLine(); // Membersihkan Buffer Enter

			switch (pilihan) {
			case 1:
				System.out.print("Masukkan No Rekening: ");
				String no = input.nextLine();
				
				System.out.print("Masukkan Nama Pemilik: ");
				String nama= input.nextLine();
				
				System.out.print("Masukkan Saldo Awal: ");
				if (!input.hasNextDouble()) {
					System.out.println("Error: Saldo awal harus berupa angka! Pembuatan rekening dibatalkan.");
					input.next();
					break;
				}
				double saldo = input.nextDouble();
				input.nextLine();
				
				System.out.print("Masukkan PIN (6 digit): ");
				String pinAwal = input.nextLine();

				System.out.println("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis | 3. Rekening VIP");
				System.out.print("Masukkan Pilihan Produk: ");
				int pilihanProduk = input.nextInt();
				input.nextLine(); // Membersihkan Buffer Enter

				if (pilihanProduk == 1) {
					System.out.print("Masukkan Suku Bunga (dalam persen): ");
					double sukuBunga = input.nextDouble();
					input.nextLine();
					
					akunAktif = new RekeningTabungan(no, nama, saldo, pinAwal, sukuBunga);
					System.out.println("Sukses: Rekening Tabungan berhasil dibuat!");
					
				} else if (pilihanProduk == 2) {
					System.out.print("Masukkan Batas Overdraft: ");
					double batasOverdraft = input.nextDouble();
					input.nextLine();
					
					akunAktif = new RekeningGiro(no, nama, saldo, pinAwal, batasOverdraft);
					System.out.println("Sukses: Rekening Giro berhasil dibuat!");
					
				} else if (pilihanProduk == 3) {
					double saldoBonus = saldo + 100000;
					
					System.out.println("Selamat Datang di Rekening VIP! Anda mendapat Bonus Saldo Rp 100.000.");
					akunAktif = new RekeningVIP(no, nama, saldoBonus, pinAwal);
					System.out.println("Sukses: Rekening VIP berhasil dibuat!");
				} else {
					System.out.println("Error: Pilihan produk tidak valid! Pembuatan rekening dibatalkan.");
					break; 
				}

				daftarRekening.add(akunAktif);
				break;

			case 2:
				if (akunAktif == null) {
					System.out.println("Error: Mohon Maaf, Anda belum memiliki nomor rekening!");
				} else if (akunAktif.isBlocked()) {
					System.out.println("Akun Anda telah diblokir karena 3x salah PIN. Transaksi tidak dapat dilakukan.");
				} else {
					System.out.print("Masukkan PIN: ");
					String pinSetor = input.nextLine();

					if (akunAktif.otentikasi(pinSetor)) {
						System.out.print("Masukkan Nominal Setor: ");
						double setor = input.nextDouble();
						input.nextLine();
						akunAktif.setorTunai(setor);
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
						if (akunAktif.isBlocked()) {
							System.out.println("Akun Anda sekarang TERBLOKIR karena 3x salah PIN.");
						}
					}
				}
				break;

			case 3:
				if (akunAktif == null) {
					System.out.println("Error: Mohon Maaf, Anda belum memiliki nomor rekening!");
				} else if (akunAktif.isBlocked()) {
					System.out.println("Akun Anda telah diblokir karena 3x salah PIN. Transaksi tidak dapat dilakukan.");
				} else {
					System.out.print("Masukkan PIN: ");
					String pinTarik = input.nextLine();

					if (akunAktif.otentikasi(pinTarik)) {
						System.out.print("Masukkan Nominal Tarik: ");
						double tarik = input.nextDouble();
						input.nextLine();
						akunAktif.tarikTunai(tarik);
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
						if (akunAktif.isBlocked()) {
							System.out.println("Akun Anda sekarang TERBLOKIR karena 3x salah PIN.");
						}
					}
				}
				break;

			case 4:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening");
				} else {
					akunAktif.cekInformasi();
				}
				break;

			case 5:
				if (daftarRekening.isEmpty()) {
					System.out.println("Error: Belum ada rekening yang terdaftar!");
				} else {
					System.out.print("Masukkan Nomor Rekening yang ingin diaktifkan: ");
					String noCari = input.nextLine();

					Rekening ditemukan = null;
					for (Rekening r : daftarRekening) {
						if (r.getNomorRekening().contentEquals(noCari)) {
							ditemukan = r;
							break;
						}
					}
					if (ditemukan != null) {
						akunAktif = ditemukan;
						System.out.println("Sukses: Akun aktif berhasil diubah ke no rekening " + akunAktif.getNomorRekening());
					} else {
						System.out.println("Error: Nomor rekening tidak ditemukan!");
					}
				}
				break;

			case 6:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening ");
				} else {
					System.out.print("Masukkan PIN: ");
					String pinMutasi = input.nextLine();

					if (akunAktif.otentikasi(pinMutasi)) {
						System.out.println("=== MUTASI REKENING ===");
						akunAktif.cetakMutasi();
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
						if (akunAktif.isBlocked()) {
							System.out.println("Akun Anda sekarang TERBLOKIR karena 3x salah PIN.");
						}
					}
				}
				break;
			
			case 7:
				if (akunAktif == null) {
			        System.out.println("Error: Anda belum membuka rekening");
			    } else if (akunAktif instanceof RekeningTabungan) {
			        // Downcasting: Rekening -> RekeningTabungan
			        RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
			        tabungan.tambahBungaAkhirBulan();
			    } else {
			        System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
			    }
			    break;

			case 0:
				isRunning = false;
				System.out.println("Sistem ditutup. Terima Kasih!");
				break;

			default:
				System.out.println("Pilihan Tidak Valid");
			}
		}
		input.close();
	}
}