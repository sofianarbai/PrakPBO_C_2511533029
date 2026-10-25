package pekan3;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);

		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif = null; // objek belum diinilisasi
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
			System.out.println("0. Keluar");
			System.out.println("Pilih Menu:");

			if (!input.hasNextInt()) {
				System.out.println("Error: Pilihan menu harus berupa angka!");
				input.next();
				continue;
			}

			int pilihan = input.nextInt();
			input.nextLine(); //Membersihkan Buffer Enter

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

			//Intansiasi Object / Menjalankan Constructor
			akunAktif = new Rekening(no, nama, saldo, pinAwal);

			//simpan ke dalam arraylist
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
						double Tarik = input.nextDouble();
						input.nextLine();
						akunAktif.tarikTunai(Tarik);
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
					System.out.println("Error: Anda bekum membuka rekening");
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

			case 0:
				isRunning = false;
				System.out.println("Sistem ditutup. Terima Kasih!");
				break;

				default:
					System.out.println("Pililhan Tidak Valid");
			}
		}
		input.close();
	}
}