let pkgs = import <nixpkgs> { };
in pkgs.haskellPackages.callPackage ./hw2.nix { }