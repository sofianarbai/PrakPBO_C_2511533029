package pekan4;

import java.util.ArrayList;

public class Rekening {
	//1. mengunci atribut dengan private
		private String nomorRekening;
		private String namaPemilik;
		private String pin; //Data sensitif
		
		// Gunakan Protected agar subclass bisa mengaksesnya langsung
		protected double saldo;
		
		// Implementasi Asosiasi (1-to-many)
		protected ArrayList<Transaksi> riwayatTransaksi;
		
		// percobaan PIN salah & status blokir
		private int percobaanGagal;
		private boolean isBlocked;

   //2. Modifikasi Constructor untuk menerima PIN awal
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;

		// Validasi PIN di dalam Constructor
		if (pinAwal.length()==6) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin = "123456";
		}

		// Inisialisasi status keamanan
		this.percobaanGagal = 0;
		this.isBlocked = false;

		// Wajib menginisialisasi ArrayList di dalam constructor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<>();

		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);

	}

	// 3. Getter untuk atribut yang diizinkan
	public String getNomorRekening() {
		return nomorRekening;

	}

	public String getNamaPemilik() {
		return namaPemilik;
	}

	// 4. Method Otentikasi Internal (Validasi Enkapsulasi)
	public boolean otentikasi(String inputPin) {
		// Jika akun sudah terblokir, langsung tolak tanpa mengecek PIN
		if (this.isBlocked) {
			return false;
		}

		if (this.pin.equals(inputPin)) {
			this.percobaanGagal = 0;
			return true;
		} else {
			this.percobaanGagal++;
			if (this.percobaanGagal >= 3) {
				this.isBlocked = true;
			}
			return false;
		}
	}

	// 5. Getter status blokir, dipanggil dari Main sebelum transaksi
	public boolean isBlocked() {
		return this.isBlocked;
	}

	public int getPercobaanGagal() {
		return this.percobaanGagal;
	}

	public void setorTunai (double nominal) {
		if (nominal > 10000 && nominal < 500000) {
			saldo += nominal;
			// Merekam riwayat (pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi (idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0! atau Transaksi lebih dari 500000");
		}
	}
	public void tarikTunai(double nominal) {
	    if (nominal < 10000) {
	        System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000");
	    }
	    else if (nominal > saldo) {
	        System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
	    }
	    else {
	    	// Merekam riwayat (pembuatan objek Transaksi di dalam method)
	    	String idTrx = "TRX-T-" + System.currentTimeMillis();
	    	Transaksi trxBaru = new Transaksi (idTrx, "Debit", nominal);
	    	riwayatTransaksi.add(trxBaru);
	        saldo -= nominal;
	        System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
	    }
	}
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini");
		} else {
			for (Transaksi t : riwayatTransaksi) {
				t.cetakDetail();
			}
		}
	}

	public void cekInformasi() {
		System.out.println("-- INFO REKENING --");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir  : Rp" + saldo);
		System.out.println("Status Akun  : " + (isBlocked ? "TERBLOKIR" : "Aktif"));
		System.out.println("---------------------");
	}
}