
package com.example.packagist.data

import com.example.packagist.model.PhpPackage

object MockPackages {

    val packages = listOf(
        PhpPackage(
            name = "laravel/framework",
            description = "The Laravel Framework.",
            version = "v12.0.0",
            downloads = "350M",
            stars = 82000,
            require = mapOf(
                "php" to "^8.2",
                "symfony/console" to "^7.0",
                "symfony/http-foundation" to "^7.0"
            )
        ),
        PhpPackage(
            name = "symfony/console",
            description = "Eases the creation of beautiful CLI applications.",
            version = "v7.0.0",
            downloads = "290M",
            stars = 11000,
            require = mapOf(
                "php" to ">=8.2",
                "symfony/polyfill-mbstring" to "^1.0"
            )
        ),
        PhpPackage(
            name = "guzzlehttp/guzzle",
            description = "Guzzle is a PHP HTTP client library.",
            version = "v7.9.0",
            downloads = "500M",
            stars = 23000,
            require = mapOf(
                "php" to "^7.2.5 || ^8.0",
                "guzzlehttp/psr7" to "^2.4"
            )
        ),
        PhpPackage(
            name = "monolog/monolog",
            description = "Sends your logs to files, sockets, inboxes and databases.",
            version = "v3.8.0",
            downloads = "450M",
            stars = 13000,
            require = mapOf(
                "php" to ">=8.1",
                "psr/log" to "^2.0 || ^3.0"
            )
        )
    )
}
