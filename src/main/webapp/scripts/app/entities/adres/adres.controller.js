'use strict';

angular.module('tiimspotApp')
    .controller('AdresController', function ($scope, $state, $modal, Adres, AdresSearch, ParseLinks) {
      
        $scope.adress = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Adres.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.adress.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.adress = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            AdresSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.adress = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.adres = {
                straat: null,
                huisnummer: null,
                busnummer: null,
                postcode: null,
                gemeente: null,
                landISO3: null,
                id: null
            };
        };
    });
