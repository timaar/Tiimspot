'use strict';

angular.module('tiimspotApp')
    .controller('PersoonController', function ($scope, $state, $modal, Persoon, PersoonSearch) {
      
        $scope.persoons = [];
        $scope.loadAll = function() {
            Persoon.query(function(result) {
               $scope.persoons = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            PersoonSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.persoons = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.persoon = {
                voornaam: null,
                naam: null,
                geslacht: null,
                geboorteDatum: null,
                id: null
            };
        };
    });
