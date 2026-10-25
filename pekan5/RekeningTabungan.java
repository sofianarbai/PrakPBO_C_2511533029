package pekan5;

public class RekeningTabungan extends Rekening {
	// Atribut spesifik yang hanya dimiliki oleh tabungan
	private double sukuBunga;
	
	//constructor Subclass
	public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		// Super () memanggil constructor kelas untuk (rekening). Wajib berada di baris pertama!
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
 	}
	
	public void tambahBungaAkhirBulan() {
		//Menghitung Bunga
		//Mengapa bisa mengakses saldo secara langsung dari class RekeningTabungan?
		double nominalBunga = saldo * (sukuBunga/100);
		saldo += nominalBunga;
		
		//Mencatat riwayat transaksi
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));
		
		System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan Rp" + nominalBunga);
	}
}