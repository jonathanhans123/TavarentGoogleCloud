<?php

namespace App\Mail;

use Carbon\Carbon;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Mail\Mailable;
use Illuminate\Mail\Mailables\Address;
use Illuminate\Mail\Mailables\Content;
use Illuminate\Mail\Mailables\Envelope;
use Illuminate\Queue\SerializesModels;

class BeritaMail extends Mailable
{
    use Queueable, SerializesModels;

    public $user;
    public $deskripsi;
    public $email;
    /**
     * Create a new message instance.
     *
     * @return void
     */
    public function __construct($pUser,$pDeskripsi,$pEmail)
    {
        $this->user = $pUser;
        $this->deskripsi = $pDeskripsi;
        $this->email = $pEmail;
    }

    /**
     * Get the message envelope.
     *
     * @return \Illuminate\Mail\Mailables\Envelope
     */
    public function envelope()
    {
        return new Envelope(
            subject: 'Berita Mail',
            from: new Address("tavarentmail@gmail.com","Tavarent")
        );
    }

    /**
     * Get the message content definition.
     *
     * @return \Illuminate\Mail\Mailables\Content
     */
    public function content()
    {
        return new Content(
            view: 'Admin.mail',
            with:[
                'user' =>$this->user,
                'email' =>$this->email,
                'deskripsi' => $this->deskripsi,
                'tanggal' => Carbon::now()->isoFormat('DD MMM YYYY')
            ]
        );
    }

    /**
     * Get the attachments for the message.
     *
     * @return array
     */
    public function attachments()
    {
        return [];
    }
}
